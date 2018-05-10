package dai.luis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dai.luis.beans.AlmacenBean;
import dai.luis.models.Almacen;
import dai.luis.service.AlmacenService;
import dai.luis.service.ProductoService;


@Controller 
public class BorrarController {
	@Autowired
	ProductoService productoService;
	
	@Autowired
	AlmacenService almacenService;
	
	@RequestMapping(value="borrarProducto", method = RequestMethod.GET)
	public String borrarProducto(
			@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="idAlmacen",required=false) Long idAlmacen,
			@RequestParam (value="idAlmacenBack",required=false) Long idAlmacenBack, 
			@RequestParam (value="nombreAlmacenBack",required=false) String nombreAlmacenBack,  
			@RequestParam (value="telefonoBack",required=false) String telefonoBack,
			ModelMap model) {			
		productoService.borrarProducto(id);
		AlmacenBean almacenBean = new AlmacenBean();
		almacenBean.setIdAlmacen(idAlmacenBack);
		almacenBean.setNombreAlmacen(nombreAlmacenBack);
		almacenBean.setTelefono(telefonoBack);
		Almacen almacen = almacenService.buscarAlmacenById(idAlmacen);
		almacenBean.setIdAlmacenEditar(idAlmacen);
		almacenBean.setNombreAlmacenEditar(almacen.getNombreAlmacen());
		almacenBean.setTelefonoEditar(almacen.getTelefono());
		model.addAttribute("command", almacen);
		model.addAttribute("almacenBeanFiltro", almacenBean);
		model.addAttribute("mensajecreado","Producto Borrado satisfactoriamente.");
		return "modificaAlmacen";
	}
	
	
	@RequestMapping(value="borrarAlmacen", method = RequestMethod.GET)
	public String borrarAlmacen(			
			@RequestParam(value="idAlmacen",required=false) Long idAlmacen,
			ModelMap model) {					
		String mensaje = almacenService.borrarAlmacen(idAlmacen);
		model.addAttribute("mensajecreado",(mensaje.equalsIgnoreCase("ko")?"ERROR al borrar Almacen.":"Almacen borrado satisfactoriamente."));
		return "almacen"; 
	}
}
