package org.alberto.papRec2022.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persona")
public class PersonaController {
	
	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view","persona/r");
		return "_t/frame";
	}
	
	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view","persona/c");
		return "_t/frame";
	}
}
