package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService paymentService;
	
	public ContractService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		double quote = (double) contract.getTotalValue() / months;
		
		for(int i = 1; i <= months; i++) {
			
			double interest = quote + paymentService.interest(quote, i);
			
			double pf = interest + paymentService.paymentFee(interest);
			
			Date dueDate = addMonths(contract.getDate(), i);
			
			Installment installment = new Installment(dueDate, pf);
			
			contract.addInstallments(installment);
		
		}
	}
	
	private Date addMonths(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);
		return calendar.getTime();
		
	}

}
