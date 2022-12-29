package order.command;

import command.CommandHandler;
import order.domain.Member;
import order.domain.Product;
import order.domain.ProductJoin;
import order.domain.ProductSangse;
import order.domain.ShipAdd;
import order.service.OrderViewService;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderViewHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrderViewHandler.process");
		HttpSession session = request.getSession(false);

		if (session == null) {
			System.out.println("세션없음");
			return "/order/default.jsp";
		} else {
			String requestMethod = request.getMethod();
			if (requestMethod.equals("GET")) {
				return null;
			} else if (requestMethod.equals("POST")) {
				int fromwhere = Integer.parseInt(request.getParameter("fromwhere"));

				if (fromwhere == 1) {
					OrderViewService orderViewService = OrderViewService.getInstance();

					long pid = Long.parseLong(request.getParameter("pid"));
					int weight = Integer.parseInt(request.getParameter("weight"));
					int amount = Integer.parseInt(request.getParameter("amount"));

					String sid = (String) session.getAttribute("auth");
//    		    	User user = session.getAttribute("authUser");
//    		    	user.getId();
					Member member = orderViewService.selectMember(sid);

					long mid = member.getMid();

//    		    	Product product = orderViewService.selectProduct(pid);
					long psid = orderViewService.selectProductSangseId(pid, weight);

					List<ProductJoin> list = orderViewService.selectProductJoin(psid, amount);
					
					
					long ordernum = orderViewService.getOrderNum();

					ShipAdd shipadd = orderViewService.selectShipAdd(mid);

					request.setAttribute("pid", pid);
					request.setAttribute("weight", weight);
					request.setAttribute("amount", amount);
					request.setAttribute("psid", psid);

					request.setAttribute("member", member);
					request.setAttribute("shipadd", shipadd);
					request.setAttribute("productlist", list);
					request.setAttribute("ordernum", ordernum);
					request.setAttribute("fromwhere", fromwhere);

					long totalprice = 0;
					int totalamount = 0;
					Iterator<ProductJoin> iterator = list.iterator();

					while (iterator.hasNext()) {
						ProductJoin pd = iterator.next();
						totalprice += pd.getAmount() * pd.getPrice();
						totalamount += pd.getAmount();
					}

					request.setAttribute("totalprice", totalprice);
					request.setAttribute("totalamount", totalamount);

					int delprice = 0;
					if (totalprice < 15000) {
						delprice = 2500;
					}

					request.setAttribute("delprice", delprice);

					response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None");
					response.setHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None");
					response.setHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");
					return "/order/order.jsp";

				} else if (fromwhere == 2) {
					return null;
				}

			}
		}
		return null;
	}
}
