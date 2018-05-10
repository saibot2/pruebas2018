package dai.luis.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dai.luis.beans.AlmacenBean;
import dai.luis.models.Almacen;
import dai.luis.models.Producto;
import dai.luis.service.AlmacenService;
import dai.luis.service.ProductoService;

@Controller 
public class ModificaController {
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private AlmacenService almacenService;
	
	@RequestMapping(value="editaAlmacen", method = RequestMethod.POST)
	public ModelAndView editaAlmacen(AlmacenBean almacenBean, ModelMap model,HttpServletRequest req) {	
		String mensaje = req.getParameter("mensajecreado");
		Almacen almacen = almacenService.buscarAlmacenById(almacenBean.getIdAlmacenEditar());
		model.addAttribute("mensajecreado", mensaje);
		model.addAttribute("command", almacen);
		return new ModelAndView("modificaAlmacen","almacenBeanFiltro",almacenBean);
	}	
	
	@RequestMapping(value="modificaAlmacen", method = RequestMethod.POST)
	public ModelAndView actualizaAlmacen(AlmacenBean almacenBean, ModelMap model,HttpServletRequest req) {	
		Almacen almacen = almacenService.buscarAlmacenById(almacenBean.getIdAlmacenEditar());
		almacen.setNombreAlmacen(almacenBean.getNombreAlmacenEditar());
		almacen.setTelefono(almacenBean.getTelefonoEditar());
		String mensaje = almacenService.updateAlmacenSession(almacen);
		model.addAttribute("mensajecreado",(mensaje.equalsIgnoreCase("ko")?"ERROR al modificar Almacen.":"Almacen modificado satisfactoriamente.") );
		model.addAttribute("command", almacen);
		return new ModelAndView("modificaAlmacen","almacenBeanFiltro",almacenBean);
	}	
	
	@RequestMapping(value="modificaProducto/{idAlmacenEditar}/{idProductoEditar}/{idAlmacen}/{nombreAlmacen}/{telefono}", method = RequestMethod.GET)
	public ModelAndView modificaProducto(
			@PathVariable Long idAlmacenEditar,
			@PathVariable Long idProductoEditar, 
			@PathVariable Long idAlmacen,
			@PathVariable String nombreAlmacen,
			@PathVariable String telefono,
			ModelMap model) {	
		//{idAlmacen}/{nombreAlmacen}/{telefono} son para poder generar la consulta inicial al volver.
		model.addAttribute("crearUpdateMsg", "Modifica Producto");
		Producto producto = productoService.buscarProducto(idProductoEditar, idAlmacenEditar);
		AlmacenBean almacenBean = setAlmacenBean(idAlmacen, nombreAlmacen, telefono); 
		model.addAttribute("almacenBeanFiltro", almacenBean);
		return new ModelAndView("modificaProducto","command",producto);	
	}
	
	@RequestMapping(value="modificaProducto", method = RequestMethod.POST)
	public String actualizaProducto(Producto producto, ModelMap model) {			
		String mensaje = productoService.updateProducto(producto);		
		model.addAttribute("mensajecreado",(mensaje.equalsIgnoreCase("ko")?"ERROR al modificar Producto.":"Producto modificado satisfactoriamente."));
		return "redirect:/consultaAlmacen.html";	
	}
	
	private AlmacenBean setAlmacenBean(Long idAlmacen, String nombreAlmacen,String telefono){
		AlmacenBean almacenBean = new AlmacenBean();
		almacenBean.setIdAlmacen( (idAlmacen==0) ? null :idAlmacen );
		almacenBean.setNombreAlmacen( (nombreAlmacen.equalsIgnoreCase("0")) ? null : nombreAlmacen );
		almacenBean.setTelefono( (telefono.equalsIgnoreCase("0")) ? null : telefono );
		return almacenBean;
	}
}
