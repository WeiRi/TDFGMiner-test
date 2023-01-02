package cluster;

import javaimpl.dfg.DFG;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
@Setter
@Getter
public class APIPoint {
    private String name;
    private ArrayList<String> structvector,textvector;
    private HashMap<String,Double> sdis,cdis;
    private int idx;
    APIPoint(String name,int idx){
        this.name=name;
        this.idx=idx;
        structvector=new ArrayList<>();
        textvector=new ArrayList<>();
        sdis=new HashMap<>();
        cdis=new HashMap<>();
    }

    public void setStructvector(String struct) {
        if(struct.length()>0){
            String temp[]=struct.split(",");
            for(int i=0;i<temp.length;i++){
                if(structvector.isEmpty()||!structvector.contains(temp[i]))
                    structvector.add(temp[i]);
            }
        }
    }

    public void setTextvector(String text) {
        if(text.length()>0){
            String temp[]=text.split(",");
            for(int i=0;i<temp.length;i++){
                if(textvector.isEmpty()||!textvector.contains(temp[i]))
                    textvector.add(temp[i]);
            }
        }
    }
    //获取和目标API间的源代码或者客户端代码距离
    public double getDis(boolean isSource, APIPoint api){
        //如果字典中没有结果，说明是计算和自身的距离，因此为0
        if(isSource)
            return sdis.getOrDefault(api.getName(),0.0);
        else
            return cdis.getOrDefault(api.getName(),0.0);
    }
    //设置距离
    public void setDis(APIPoint b, DFG dfg){
        String aname,bname;
        aname=this.name;
        bname=b.getName();
        //设置源代码距离
        double sourcedis=1;
        //计算文本相似度和结构相似度
        double textsim,structsim;
        if(this.textvector.isEmpty()||b.textvector.isEmpty()){
            textsim=0;
        }else{
            ArrayList<String> combinetext=new ArrayList<>();
            int textcount=0;
            for(int i=0;i<this.textvector.size();i++)
                combinetext.add(this.textvector.get(i));
            for(int i=0;i<b.textvector.size();i++){
                if(combinetext.contains(b.textvector.get(i)))
                    textcount++;
                else
                    combinetext.add(b.textvector.get(i));
            }
            textsim=textcount/combinetext.size();
        }
        if(this.structvector.isEmpty()||b.structvector.isEmpty()){
            structsim=0;
        }else{
            ArrayList<String> combinestruct=new ArrayList<>();
            int structcount=0;
            for(int i=0;i<this.structvector.size();i++)
                combinestruct.add(this.structvector.get(i));
            for(int i=0;i<b.structvector.size();i++){
                if(combinestruct.contains(b.structvector.get(i)))
                    structcount++;
                else
                    combinestruct.add(b.structvector.get(i));
            }
            structsim=structcount/combinestruct.size();
        }
        sourcedis-=(textsim+structsim)/2;
        this.sdis.put(bname,sourcedis);
        b.sdis.put(aname,sourcedis);
        //设置客户端代码距离
        double clientdis;
        int nodecount=dfg.getNodeCount();
        int[]visited=new int[nodecount];
        visited[this.idx]=1;
        clientdis=search(dfg,this.idx,b.getIdx(),0,false,visited);
        this.cdis.put(bname,clientdis);
        b.cdis.put(aname,clientdis);
    }
    //深度优先搜索，找寻最短路径
    public int search(DFG dfg,int now,int target,int depth,boolean finished,int[]visited){
        int arccount=dfg.getNode(now).getDegree();
        int result=999;
        int vistedarcs=0;
        for(int i=0;i<dfg.getEdgeCount()&&vistedarcs<arccount&&!finished;i++){
            var nowedge=dfg.getEdge(i);
            if(nowedge.getNodeA().getIndex()==now||nowedge.getNodeB().getIndex()==now){
                int nodeb=nowedge.getNodeB().getIndex();
                if(nodeb==now)
                    nodeb=nowedge.getNodeA().getIndex();
                //找到目标节点
                if(nodeb==target){
                    finished=true;
                    result=depth+1;
                    return result;
                }else if(visited[nodeb]==0){
                    visited[nodeb]=1;
                    result=Math.min(result,search(dfg,nodeb,target,depth+1,finished,visited));
                    visited[nodeb]=0;
                }
            }
        }
        return result;
    }
}
