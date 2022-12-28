package managerpage.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import managerpage.domain.Member;
import managerpage.service.ManagerListService;

public class ViewHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		// TODO Auto-generated method stub
		String requestMethod = request.getMethod();
        String meID = request.getParameter("meID");

        if (requestMethod.equals("GET")){
            return "/managerpage/view/"+ meID +".jsp";
        }
		return null;
		
	}

}
