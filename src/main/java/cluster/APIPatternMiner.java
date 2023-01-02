package cluster;


import javaimpl.dfg.DFG;
import kg.CodeUtil;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import org.christopherfrantz.dbscan.DBSCANClusterer;
import org.christopherfrantz.dbscan.DBSCANClusteringException;

import java.util.ArrayList;

@Getter
@Setter
public class APIPatternMiner {
    private CodeUtil codeUtil;
    private DFG dfg;
    private ArrayList<APIPoint> points;
    public APIPatternMiner(CodeUtil codeUtil,DFG dfg){
        this.codeUtil=codeUtil;
        this.dfg=dfg;
        points=new ArrayList<>();
    }
    @Getter
    @Setter
    //存储最终的模式
    public class MyPattern{
        private ArrayList<String> apis;
        MyPattern(){
            apis= new ArrayList<>();
        }
        MyPattern(ArrayList<APIPoint> points){
            apis= new ArrayList<>();
            for(int i=0;i<points.size();i++)
                apis.add(points.get(i).getName());
        }
        private String getAPIs(){
            String s="";
            for(int i=0;i<apis.size();i++)
                s+=apis.get(i)+",";
            return s;
        }

        @Override
        public String toString() {
            return "MyPattern{" +
                    "apis=" + getAPIs() +
                    '}';
        }
    }
    //计算APIPoint其中的距离
    private boolean builder(){
        if(getAllAPIs()>1){
            int now=0;
            while (now<points.size()-1){
                for(int next=now+1;next<points.size();next++){
                    points.get(now).setDis(points.get(next),dfg);
                }
                now++;
            }
            return true;
        }else
            return false;
    }
    //获取全部API，初始化为APIPoint
    private int getAllAPIs(){
        int count=0;
        for(int i=0;i<dfg.getNodeCount();i++){
            var node=dfg.getNode(i);
            System.out.println(node.getLabel().getType());
            System.out.println(node.getLabel().getInfo());
            if(node.getLabel().getType().getName().equals("#METHOD_INVOCATION")){
                var methodentity=codeUtil.getMethod(node.getLabel().getInfo());
                if(methodentity!=null){
                    count++;
                    APIPoint point=new APIPoint(node.getLabel().getInfo(),node.getIndex());
                    point.setStructvector(methodentity.getStructVector());
                    point.setTextvector(methodentity.getTextVector());
                    points.add(point);
                }
            }
        }
        return count;
    }
    //挖掘算法
    public ArrayList<MyPattern> miner() throws DBSCANClusteringException {
        ArrayList<MyPattern> result=new ArrayList<>();
        //TODO: 实现模式挖掘，存入result
        if(builder())
        {
            System.out.println("have apis!");
            //先进行源代码计算
            APISourceDisMetric sourceDisMetric=new APISourceDisMetric();
            DBSCANClusterer<APIPoint> sourceCluster=new DBSCANClusterer<APIPoint>(points,2,1,sourceDisMetric);
            ArrayList<ArrayList<APIPoint>> sourceresult=sourceCluster.performClustering();
            //再进行客户端代码计算
            ArrayList<APIPoint> nowpoints=new ArrayList<>();
            for(int i=0;i<sourceresult.size();i++){
                if(sourceresult.get(i).size()>1)
                    nowpoints.addAll(sourceresult.get(i));
            }
            APIClientDisMetric clientDisMetric=new APIClientDisMetric();
            DBSCANClusterer<APIPoint> clientCluster=new DBSCANClusterer<APIPoint>(nowpoints,2,5,clientDisMetric);
            ArrayList<ArrayList<APIPoint>> clientresult=clientCluster.performClustering();
            for(int i=0;i<clientresult.size();i++){
                MyPattern pattern=new MyPattern(clientresult.get(i));
                result.add(pattern);
            }
        }
        System.out.println(result);
        return result;
    }
}
