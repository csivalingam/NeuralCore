package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.wallet.Wallet;


@Path("/wallet")
public interface WalletService {
	@POST
	@Path("getwallets")
	@Produces("text/xml")
	List<Wallet> getWallets(@FormParam("memberid") Long memberId);
}
