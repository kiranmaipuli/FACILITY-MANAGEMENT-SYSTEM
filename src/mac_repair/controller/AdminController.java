package mac_repair.controller;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import mac_repair.data.UserDAO;
import mac_repair.model.Search;
import mac_repair.model.SearchMessage;
import mac_repair.model.User;
import mac_repair.model.UserError;
import mac_repair.util.DropdownUtils;

@WebListener
@WebServlet("/admin")
public class AdminController extends HttpServlet implements HttpSessionListener {

	private static final long serialVersionUID = 1L;
	HttpSession session;
	User currentUser;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();   //defining the session parameter
		if (session.getAttribute("current_user") != null)
			currentUser =  (User) session.getAttribute("current_user");
		
		super.service(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session.setAttribute("current_role", "admin");
		
		session.removeAttribute("error");
		session.removeAttribute("message");
		session.removeAttribute("success_message");
		session.removeAttribute("USERS");
		session.removeAttribute("change_role");
		session.removeAttribute("user_search");
		session.removeAttribute("UPDATEUSER");
		
//		user not logged in
		if (currentUser == null) {
			
			response.sendRedirect("login");
		}
//		logged in
		else {

//			Show Selected User Details
			if (request.getParameter("user_details") != null) {
				
				User user = UserDAO.getUserByUsername(request.getParameter("user_details"));

				session.setAttribute("USERS", user);
				
				request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
				request.getRequestDispatcher("/user_details.jsp").include(request, response);
				if (!user.getRole().equals("Admin")) {
					session.setAttribute("role_dropdown", DropdownUtils.getAllRolesDropdown());
					request.getRequestDispatcher("/user_role_form.jsp").include(request, response);
				}
			}

//			Open edit other users profile
			else if (request.getParameter("edit_user") != null) {
				String username = request.getParameter("edit_user");
				
				User user = UserDAO.getUserByUsername(username);
				session.setAttribute("UPDATEUSER", user);
				session.setAttribute("state_dropdown", DropdownUtils.getStateDropdown());
				
				request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
				request.getRequestDispatcher("/edit_user_form.jsp").include(request, response);
			}
 			
//			Open Search page
			else if (request.getParameter("search") != null) {
				
				request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
				request.getRequestDispatcher("/user_search_form.jsp").include(request, response);
			}
			
//			Open Admin Home
			else {
				
				request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
				request.getRequestDispatcher("/home_admin.jsp").include(request, response);
			}
			
			if (session.getAttribute("current_user") == null)
				session.setAttribute("current_user", currentUser);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");  //defining the action parameter
		session.setAttribute("current_role", "admin");
		
		session.removeAttribute("error");
		session.removeAttribute("message");
		session.removeAttribute("success_message");
		session.removeAttribute("USERS");
		session.removeAttribute("change_role");
		session.removeAttribute("user_search");
		session.removeAttribute("UPDATEUSER");
		
//		user not logged in
		if (currentUser == null) {
			response.sendRedirect("login");
		}
//		logged in
		else {
			
//			search user
			if(action.equals("search_user")) {
				Search userSearch = getSearchParam(request); 
				SearchMessage userSearchMessage = new SearchMessage();
				ArrayList<User> listUsers = new ArrayList<User>();
				
				userSearchMessage = userSearch.validateSearch(action);
				if (!userSearchMessage.getSearchErrorMessage().equals("")) {
//					set error messages
					session.setAttribute("user_search", userSearch);
					session.setAttribute("message", userSearchMessage);
	
//					set jsp pages
					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/user_search_form.jsp").include(request, response);
				}
				
				else {
//					if no error messages
					
//					search by username
					if(userSearch.getSearchFilter().equals("1")) {
						listUsers.addAll(UserDAO.searchUsersByUsername(userSearch.getSearchText()));
					}
//					search by role
					else if(userSearch.getSearchFilter().equals("2")) {
						listUsers.addAll(UserDAO.searchUsersByRole(userSearch.getSearchText()));
					}
//					show all
					else {
						listUsers.addAll(UserDAO.listUsers());
						userSearch.setSearchText("");
					}
					session.setAttribute("user_search", userSearch);
					session.setAttribute("USERS", listUsers);
					
					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/user_search_form.jsp").include(request, response);
					if(listUsers.isEmpty()) {
						request.getRequestDispatcher("/users_list_empty.jsp").include(request, response);
					} else {
						request.getRequestDispatcher("/users_list.jsp").include(request, response);
					}
				}
			}
			
//			Change role
			else if (action.equals("change_role")){
				User updatedUser = new User();
				UserError userErrorMsgs = new UserError();
				
				String username, newRole;
				User user;
				
				session.removeAttribute("errorMsgs");
				
				updatedUser = getChangeRoleParam(request);
				userErrorMsgs = updatedUser.validateUser(action);
				
				if (!userErrorMsgs.getErrorMsg().equals("")) {
//					if error messages
					username = updatedUser.getUsername();
					user = UserDAO.getUserByUsername(username);
					
					session.setAttribute("errorMsgs", userErrorMsgs);
					session.setAttribute("USERS", user);

					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/user_details.jsp").include(request, response);
					request.getRequestDispatcher("/user_role_form.jsp").include(request, response);
				} else {
//					if no error messages
					
					username = updatedUser.getUsername();
					newRole = updatedUser.getRole();
					user = UserDAO.getUserByUsername(username);
					
					UserDAO.updateDetails(username, newRole); //update database
					session.setAttribute("success_message", "role is updated");
					user.setRole(newRole);
					
					session.setAttribute("USERS", user);
					session.setAttribute("change_role", updatedUser);
					
					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/user_details.jsp").include(request, response);
					request.getRequestDispatcher("/user_role_form.jsp").include(request, response);
				}
			}

			else if(action.equals("edit_user")) {
				
				User updateuser = new User();
				UserError userErrorMsgs = new UserError();

				updateuser = getUpdateProfileParam(request);
				userErrorMsgs = updateuser.validateUser(action);
				
				session.removeAttribute("errorMsgs");
				
				if (!userErrorMsgs.getErrorMsg().equals("")) {
 //					if error messages
					session.setAttribute("errorMsgs", userErrorMsgs);
					session.setAttribute("UPDATEUSER", updateuser);
					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/edit_user_form.jsp").include(request, response);
				}
				else {
//					if no error messages

					//update database except role
					UserDAO.updateProfile(updateuser); 
					session.setAttribute("success_message", "Profile has been updated!!!!!!!!");
					session.setAttribute("UPDATEUSER", updateuser);
					session.removeAttribute("errorMsgs");
					
					request.getRequestDispatcher("/menu_admin.jsp").include(request, response);
					request.getRequestDispatcher("/edit_user_form.jsp").include(request, response);
				}
				
			}
			
			if (session.getAttribute("current_user") == null)
				session.setAttribute("current_user", currentUser);
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		currentUser = null;
		HttpSessionListener.super.sessionDestroyed(se);
	}
	
	public Search getSearchParam(HttpServletRequest request){
		Search userSearch = new Search();
		
		userSearch.setSearchText(request.getParameter("search_text"));
		userSearch.setSearchFilter(request.getParameter("search_filter"));

		return userSearch; 
	}
	
	private User getChangeRoleParam (HttpServletRequest request) {
		User user = new User();
		
		user.setUsername(request.getParameter("username"));
		user.setRole(request.getParameter("role"));
				
		return user;
	}
	
	private User getUpdateProfileParam(HttpServletRequest request) {
		
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setUtaId(request.getParameter("utaid"));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		user.setStreet(request.getParameter("street"));
		user.setCity(request.getParameter("city"));
		user.setState(request.getParameter("state"));
		user.setZipcode(request.getParameter("zipcode"));
		user.setRole(request.getParameter("role"));
		
		return user;
	}
}
