package org.sid.web;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sid.dao.ClientRepository;
import org.sid.dao.InterventionRepository;
import org.sid.dao.ProduitRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.sid.entities.Client;
import org.sid.entities.Intervention;
import org.sid.entities.Modelmap;
import org.sid.entities.Produit;
import org.sid.entities.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/api")
public class WebServiceController {
	@Autowired
	private InterventionRepository IRepository ;
	@Autowired
	private TechnicienRepository TRepository ;
	@Autowired
	private ProduitRepository PRepository ;
	@Autowired
	private ReclamationRepository RRepo;
	@Autowired
	private ClientRepository CRepo;
	

	
	
	
	
	@GetMapping("/editProfil")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public String editPro (HttpServletRequest request) throws NumberFormatException, ParseException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		 
		 
		 Client cl=CRepo.findById(Long.parseLong(request.getParameter("idc"))).get();
		
		PasswordEncoder bcpe=new BCryptPasswordEncoder();
		
		Client client= new Client (
				
				
				
				
				request.getParameter("sexe") ,  request.getParameter("nom") , 
				request.getParameter("prenom"), 
				Integer.parseInt(request.getParameter("idc")), 
				formatter.parse(request.getParameter("dateN")) , 
				request.getParameter("adresse"), Integer.parseInt(request.getParameter("mobile")),
				request.getParameter("mail"),
				request.getParameter("password"),request.getParameter("username")
				); 
		
		
			
		if(client.getDateN()!=null)
			cl.setDateN(client.getDateN());
			
			if(client.getAdresse()!=null && !client.getAdresse().isEmpty())
				cl.setAdresse(client.getAdresse());
			
			if(client.getCin()!=null  && !(client.getCin()!=0))
				cl.setCin(client.getCin() );
			
			if(client.getMail()!=null  && !client.getMail().isEmpty())
				cl.setMail(client.getMail());
			
			if(client.getMobile()==0)
				cl.setMobile(client.getMobile());
			
			if(client.getNom()!=null && !client.getNom().isEmpty())
				cl.setNom(client.getNom());
			
			if(client.getPrenom()!=null && !client.getPrenom().isEmpty())
				cl.setPrenom(client.getPrenom());
			

			
			if(client.getPassword()!=null && !client.getPassword().isEmpty() ) {
				cl.setPassword(bcpe.encode(client.getPassword()));
			}
		CRepo.save(cl) ;
		return "done";
		
	}
	
	@GetMapping("/editrec")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public String editrec(HttpServletRequest request) {
		Client c=CRepo.findById(Long.parseLong(request.getParameter("idc"))).get();
		System.out.println("idddddddddddddddddd"+request.getParameter("idr"));
		Reclamation reclamation=new Reclamation
				(Long.parseLong(request.getParameter("idr")), 
				request.getParameter("Fixe"),
				request.getParameter("addresse"), 
				Integer.parseInt(request.getParameter("codeP")), 
				request.getParameter("typeR"),
				request.getParameter("explication"), c);
		

		Reclamation rec=RRepo.findById(reclamation.getIdR()).get();
		
		if(reclamation.getAddresse()==null || reclamation.getAddresse().isEmpty()) 
			reclamation.setAddresse(rec.getAddresse());
		if(reclamation.getFixe()==null || reclamation.getFixe().isEmpty()) 
			reclamation.setFixe(rec.getFixe());
		if(reclamation.getExplication()==null || reclamation.getExplication().isEmpty()) 
			reclamation.setExplication(rec.getExplication());
		if(reclamation.getTypeR()==null || reclamation.getTypeR().isEmpty()) 
			reclamation.setTypeR(rec.getTypeR());
		if(reclamation.getCodeP()==0  ) 
			reclamation.setCodeP(rec.getCodeP());
	 
		if(reclamation.getProduit()==null)
			reclamation.setProduit(PRepository.findById(Long.parseLong(request.getParameter("idp"))).get());
		
		
			
			
		RRepo.save(reclamation);
		return "done";
		 
	}
	
	@GetMapping("/apiMap")
	public List<Modelmap> apiMap() throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		  List<Intervention> ls= IRepository.findlocations(java.sql.Date.valueOf(localDate));
		  List<Modelmap> lsmap=new ArrayList<>();
		  for(Intervention l:ls)
			{
			  Modelmap m=new Modelmap(l.getIdInt(),l.getLocalisation(),l.getTechnicien().getNom(),l.getTechnicien().getPrenom(),l.getDateInt().toString());


			  String sURL = "https://api.mapbox.com/geocoding/v5/mapbox.places/"+URLEncoder.encode(l.getLocalisation(), "UTF-8").replace(" ", "%20")+
					  ".json?proximity=36.809507,10.138392&access_token=pk.eyJ1Ijoic2FtaWtyIiwiYSI6ImNrOHRieWk3dDBuaTQzbGxvZDh2ZGJrZjgifQ.ZXvwJ489e09-HnnWfWpWtA&limit=1";
	          String json = readUrl(sURL);
	          JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject(); 
	          JsonArray arr = jsonObject.getAsJsonArray("features");
	          JsonArray center = arr.get(0).getAsJsonObject().get("center").getAsJsonArray();
	          double lat=center.get(0).getAsDouble();
	          double lan=center.get(1).getAsDouble();
	          System.out.println(lat+" "+lan);
	          m.setLat(lat+"");
	          m.setLon(lan+"");
	          lsmap.add(m);
			} 
return lsmap;
	}
	


	
	private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

	@GetMapping("/techstat")
	public List<Object> prod() {


		Map<String,List<Object>> mp=new HashMap<String,List<Object>>();

				
		//mp.put("techstat", lstat);
		
		
				
		
		
		return IRepository.interstat();
				
		
		
	}
	@GetMapping("/stat")
	public Map<String,List<Object>> techstat() {


		Map<String,List<Object>> mp=new HashMap<String,List<Object>>();


		mp.put("techstat", TRepository.techstat());
		

		mp.put("interstat", IRepository.interstat());
		

		mp.put("prodstat", PRepository.prodstat());
		
		
				
		
		
		return mp ;
				
		
		
	}
	
	//ionic
	
	@RequestMapping("/listreclam")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public List<Object> listreclam(@RequestParam("idc") Long idc) {

		//list des reclamations pour un client donne id en param

		return RRepo.clientreclam(idc) ;
				
		
		
	}
	
	@RequestMapping("/listprod")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public List<Produit> listprod() {

		//liste produit 
		//fi ajout d'un reclam test7a9ha bech t3abi el select list eli bech ya5tar beha el produit eli bech ya3mlo reclamation

		return PRepository.findAll() ;
				
		
		
	}
	
	@RequestMapping("/allreclam")
	public List<Reclamation> getAll()
	{
		List<Reclamation> recs = RRepo.findAll();
		return recs;
	}
	
	@RequestMapping("/getreclamtionid")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public Reclamation getReclamtionId(HttpServletRequest  request)
	{
		String idr= request.getParameter("idr"); 
		Reclamation rec = RRepo.findById(Long.parseLong(idr)).get();
		return rec;
	}
	
	
	//Fixe=147852&addresse=tunis&codeP=1478&typeR=type&explication=sometest
	@RequestMapping("/ajoutreclam")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
    public String ajoutreclam(HttpServletRequest  request) {
        String idp= request.getParameter("idp");
        String idc= request.getParameter("idc");
        Reclamation reclam=new Reclamation(request.getParameter("Fixe") , request.getParameter("addresse"),
                Integer.parseInt(request.getParameter("codeP")) , request.getParameter("typeR") ,
                request.getParameter("explication") );

        Produit p=PRepository.findById(Long.parseLong(idp)).get();
        Client c=CRepo.findById(Long.parseLong(idc)).get();
        reclam.setClient(c);
        reclam.setProduit(p);


        RRepo.save(reclam) ;
        
        return "done";



    }
				
		
		
	
	
	@RequestMapping("/updateprofile")
	public void updateprofile(@RequestBody Client client) {

//ken ma5dmtch men loul nrml badelha kima lokhrin bfazet httpservletrequest 
		// behi ama lezm ta3ml creation te3 client Client client=new Client(request.getparametre("idc")..... 
		// behi

		PasswordEncoder bcpe=new BCryptPasswordEncoder() ;
		
		client.setActive(true);
		client.setRole("CLIENT");
		Client cl=CRepo.findById(client.getId()).get();
		
		if(client.getDateN()==null)
		client.setDateN(cl.getDateN());
		
		if(client.getAdresse()==null)
			client.setAdresse(cl.getAdresse());
		if(client.getCin()==null)
			client.setCin(cl.getCin());
		if(client.getMail()==null)
			client.setMail(cl.getMail());
		if(client.getMobile()==0)
			client.setMobile(cl.getMobile());
		if(client.getNom()==null)
			client.setNom(cl.getNom());
		if(client.getPrenom()==null)
			client.setPrenom(cl.getPrenom());
		

		
		if(client.getPassword()!=null && !client.getPassword().isEmpty() ) {
			client.setPassword(bcpe.encode(client.getPassword()));
		}else {
			client.setPassword(CRepo.ChercherClientusername(client.getUsername()).getPassword());
		}
		CRepo.save(client);
				
		//change
		
		
		
		
	}
	
	@RequestMapping("/login")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public String LoginApi(HttpServletRequest request) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		Client c=CRepo.ChercherClientusername(username);
		if(c!=null) {
	    	PasswordEncoder bcpe=new BCryptPasswordEncoder() ;
 
			if(bcpe.matches(password, c.getPassword())) {
				return "shih";
			}else {
				return "ghalt";
			}
			
		}else {
			return "moch mawjoud";
		}
		
		 
	}
	 
	
	@RequestMapping("/deleterec")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:9090"})
	public String DeleteReclam(HttpServletRequest request) {
		String idrec=request.getParameter("idrec");
		

		RRepo.deleteById(Long.parseLong(idrec));
		return "done";
	}
 
	
	 
	
}
