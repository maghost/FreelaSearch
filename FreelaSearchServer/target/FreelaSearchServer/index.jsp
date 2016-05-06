<%@page import="org.freelasearch.entities.Anunciante"%>
<%@page import="org.freelasearch.dao.AnuncianteDao"%>
<%@page import="org.freelasearch.entities.Contratacao"%>
<%@page import="org.freelasearch.dao.ContratacaoDao"%>
<%@page import="org.freelasearch.entities.Freelancer"%>
<%@page import="org.freelasearch.dao.FreelancerDao"%>
<%@page import="java.util.List"%>
<%@page import="org.freelasearch.entities.Categoria"%>
<%@page import="org.freelasearch.utils.DaoFactory"%>
<%@page import="org.freelasearch.dao.CategoriaDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Categorias:</h3>
	<p>
		<%
			CategoriaDao categoriaDao = DaoFactory.categoriaInstance();
			List<Categoria> categorias = categoriaDao.findAll();

			out.println("<b>Total: " + categorias.size() + "</b><br />");
			for (Categoria categoria : categorias) {
				out.println("<b>Categoria #" + categoria.getId() + "</b><br />");
				out.println(categoria.toString() + "<br /><br />");
			}
		%>
	</p>
	<br />
	<hr />
	<h3>Freelancers:</h3>
	<p>
		<%
			FreelancerDao freelancerDao = DaoFactory.freelancerInstance();
			List<Freelancer> freelancers = freelancerDao.findAll();

			out.println("<b>Total: " + freelancers.size() + "</b><br />");
			for (Freelancer freelancer : freelancers) {
				out.println("<b>Freelancer #" + freelancer.getId() + "</b><br />");
				out.println("<b>" + freelancer.getUsuario().getNome() + "</b><br />" + freelancer.toString() + "<br /><br />");
			}
		%>
	</p>
	<br />
	<hr />
	<h3>Contratações:</h3>
	<p>
		<%
			ContratacaoDao contratacaoDao = DaoFactory.contratacaoInstance();
			List<Contratacao> contratacoes = contratacaoDao.findAll();

			out.println("<b>Total: " + contratacoes.size() + "</b><br />");
			for (Contratacao contratacao : contratacoes) {
				out.println("<b>Contatação #" + contratacao.getId() + "</b><br />");
				out.println(contratacao.toString() + "<br /><br />");
			}
		%>
	</p>
	<br />
	<hr />
	<h3>Anunciantes:</h3>
	<p>
		<%
			AnuncianteDao anuncianteDao = DaoFactory.anuncianteInstance();
			List<Anunciante> anunciantes = anuncianteDao.findAll();

			out.println("<b>Total: " + anunciantes.size() + "</b><br />");
			for (Anunciante anunciante : anunciantes) {
				out.println("<b>Anunciante #" + anunciante.getId() + "</b><br />");
				out.println(anunciante.toString() + "<br /><br />");
			}
		%>
	</p>
</body>
</html>