package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.dao.wallet.WalletDao;
import net.zfp.entity.Domain;
import net.zfp.entity.User;
import net.zfp.entity.wallet.Wallet;


public class WalletServiceImpl implements WalletService {
	
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private WalletDao<Wallet> walletDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Wallet> getWallets(Long memberId){
		System.out.println("Entered Wallets :: " + new Date());
		List<Wallet> wallets = new ArrayList<Wallet>();
		
		Wallet wallet = walletDao.getWallet(memberId);
		
		System.out.println(wallet.getCommunity().getName());
		return wallets;
	}
}
