package com.belajarspring.BelajarSpringBoot.model;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ontologyModel {
    public List<JSONObject> searchById(String key)
    {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "prodi.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader,null);

            String sprql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX prodi: <http://www.TAD306.com/ontologies/pencarianprodi#>\n" +
                    "PREFIX onto: <http://www.ontotext.com/>\n" +
                    "\n" +
                    "SELECT ?programStudi ?prodi ?akreditasi ?fasilitas ?pt ?id\n" +
                    "\n" +
                    "WHERE {?programStudi\tprodi:memilikiAkreditasiProdi ?akreditasi;\n" +
                    "prodi:memilikiProdiSejenis?prodi;\n" +
                    "prodi:menyediakanFasilitas ?fasilitas;\n" +
                    "prodi:memilikiId ?id;\n" +
                    "prodi:terdapatDi ?pt.\n" +
                    "FILTER regex(?prodi, '"+ key +"',\"i\").\n" +

                    "}\n" +
                    "\n" +
                    "ORDER BY ASC(?akreditasi)";
//            FILTER (?programStudi = 'Prodi_a')
            Query query = QueryFactory.create(sprql);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet resultSet = qe.execSelect();
            int x=0;
            while (resultSet.hasNext()) {
                x++;
                JSONObject obj = new JSONObject();
                QuerySolution solution = resultSet.nextSolution();
                System.out.println(solution.get("programStudi").toString());
                obj.put("programStudi",solution.get("programStudi").toString());
                obj.put("prodi",solution.get("prodi").toString());
                obj.put("akreditasi",solution.get("akreditasi").toString());
                obj.put("fasilitas",solution.get("fasilitas").toString());
                obj.put("pt",solution.get("pt").toString());
                obj.put("id",solution.get("id").toString());

                list.add(obj);
            }
            System.out.println(x);
            return list;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<JSONObject> detailById(String key){
        List<JSONObject> list = new ArrayList<>();
        String fileName = "prodi.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader,null);

            String sprql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX prodi: <http://www.TAD306.com/ontologies/pencarianprodi#>\n" +
                    "PREFIX onto: <http://www.ontotext.com/>\n" +
                    "\n" +
                    "SELECT ?programStudi ?akreditasi ?akreditasiPt ?pt ?fasilitas ?jenjang ?fakultas ?alamat ?id ?linkWeb \n" +
                    "WHERE\n" +
                    "  {\n" +
                    "    ?programStudi prodi:memilikiId ?id ;\n" +
                    "   prodi:memilikiAkreditasiProdi ?akreditasi;" +
                    "   prodi:terdapatDi ?pt;" +
                    "   prodi:memilikiJenjang ?jenjang;" +
                    "   prodi:memilikiLink ?linkWeb;" +
                    "   prodi:memilikiAkreditasiPT ?akreditasiPt;" +
                    "   prodi:menyediakanFasilitas ?fasilitas;" +
                    "   prodi:beradaDi ?fakultas;" +
                    "   prodi:memilikiAlamat ?alamat;" +
                    "    FILTER (?id = '"+ key +"')\n" +
                    "  }\n";
//            FILTER (?programStudi = 'Prodi_a')
            Query query = QueryFactory.create(sprql);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet resultSet = qe.execSelect();

            int x=0;
            while (resultSet.hasNext()) {
                x++;
                JSONObject obj = new JSONObject();
                QuerySolution solution = resultSet.next();
                System.out.println(solution.get("programStudi").toString().split("#")[1]);
                obj.put("programStudi",solution.get("programStudi").toString());
                obj.put("akreditasi",solution.get("akreditasi").toString().split("#")[1]);
                obj.put("akreditasiPt",solution.get("akreditasiPt").toString().split("#")[1]);
                obj.put("fasilitas",solution.get("fasilitas").toString().split("#")[1]);
                obj.put("fakultas",solution.get("fakultas").toString().split("#")[1]);
                obj.put("pt",solution.get("pt").toString().split("#")[1]);
                System.out.println(solution.get("alamat").toString());
                obj.put("alamat",solution.get("alamat").toString());
                obj.put("jenjang",solution.get("jenjang").toString());
                obj.put("linkWeb",solution.get("linkWeb").toString());
                obj.put("id", key);
                list.add(obj);
            }
            System.out.println(x);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
