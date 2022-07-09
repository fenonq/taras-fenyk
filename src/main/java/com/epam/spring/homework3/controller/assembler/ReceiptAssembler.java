package com.epam.spring.homework3.controller.assembler;

import com.epam.spring.homework3.controller.ReceiptController;
import com.epam.spring.homework3.controller.model.ReceiptModel;
import com.epam.spring.homework3.dto.ReceiptDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReceiptAssembler extends RepresentationModelAssemblerSupport<ReceiptDto, ReceiptModel> {

    public static final String GET_ALL_REL = "get_all_receipts";
    public static final String GET_REL = "get_receipt";
    public static final String CREATE_REL = "create_receipt";
    public static final String DELETE_REL = "delete_receipt";
    public static final String NEXT_STATUS_REL = "next_status_receipt";
    public static final String CANCEL_RENEW_REL = "cancel_renew_receipt";

    public ReceiptAssembler() {
        super(ReceiptController.class, ReceiptModel.class);
    }

    @Override
    public ReceiptModel toModel(ReceiptDto entity) {
        ReceiptModel receiptModel = new ReceiptModel(entity);
        Long managerId = entity.getManager() == null ? 0L : entity.getManager().getId();

        Link getAll = linkTo(methodOn(ReceiptController.class).getAllReceipts()).withRel(GET_ALL_REL);
        Link get = linkTo(methodOn(ReceiptController.class).getReceipt(entity.getId())).withRel(GET_REL);
        Link create = linkTo(methodOn(ReceiptController.class).makeOrder(entity.getCustomer().getId()))
                .withRel(CREATE_REL);
        Link delete = linkTo(methodOn(ReceiptController.class).deleteReceipt(entity.getId()))
                .withRel(DELETE_REL);
        Link next = linkTo(methodOn(ReceiptController.class).nextStatus(entity.getId(),
                managerId)).withRel(NEXT_STATUS_REL);
        Link cancel = linkTo(methodOn(ReceiptController.class).cancelOrRenewReceipt(entity.getId(),
                managerId)).withRel(CANCEL_RENEW_REL);

        receiptModel.add(getAll, get, create, delete, next, cancel);

        return receiptModel;
    }

}
