	package org.sid.web;



import java.util.List;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProduitController {
	@Autowired
private ProduitRepository produitRepository ;
	@GetMapping("/admin/produit")
	public String chercher(Model model , @RequestParam(name="page", defaultValue="0") int page ,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		
		Page<Produit>pageProduits=produitRepository.findByDesignationContains("%"+mc+"%",PageRequest.of(page, 5)) ;
		model.addAttribute("listProduits",pageProduits.getContent()) ;
		model.addAttribute("pages",new int[pageProduits.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		model.addAttribute("motCle" , mc) ;
		return "/produit/Produit" ;
	}
	
	@GetMapping("/admin/deleteP")
	public String delete (Long id,int page , String motCle) {
		produitRepository.deleteById(id);
		return"redirect:/admin/produit?page="+page+"&motCle"+motCle;
	}
	
	
	@PostMapping("/admin/saveP")
	public String save (Model model , @Valid Produit produit , BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return"FormProduit" ;
		produitRepository.save(produit) ; 
		return "redirect:/admin/produit" ; 
	}
	
	@GetMapping("/admin/editP")
	public String form (Model model , Long id) {
Produit produit=produitRepository.findById(id).get();
		model.addAttribute("produit",produit) ; 

		return "Produit/EditProduit" ; 
	}
	@GetMapping("/admin/formProduit")
	public String form (Model model) {
		model.addAttribute("produit",new Produit()) ; 
		
		return "produit/FormProduit" ; 
	}
	

	
}
