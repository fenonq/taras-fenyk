package com.epam.spring.homework3.api;

import com.epam.spring.homework3.controller.model.ReceiptModel;
import com.epam.spring.homework3.dto.ReceiptDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Receipt management api")
@RequestMapping("/api/v1/receipts")
public interface ReceiptApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<ReceiptModel> getAllReceipts();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Receipt id")
    })
    @ApiOperation("Get receipt by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    ReceiptModel getReceipt(@PathVariable Long id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id")
    })
    @ApiOperation("Create receipt")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}")
    ReceiptModel makeOrder(@PathVariable Long userId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Receipt id")
    })
    @ApiOperation("Delete receipt")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteReceipt(@PathVariable Long id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiptId", paramType = "path", required = true, value = "Receipt id"),
            @ApiImplicitParam(name = "managerId", paramType = "path", required = true, value = "Manager id")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/next-status/{receiptId}/manager/{managerId}")
    ReceiptModel nextStatus(@PathVariable Long receiptId, @PathVariable Long managerId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiptId", paramType = "path", required = true, value = "Receipt id"),
            @ApiImplicitParam(name = "managerId", paramType = "path", required = true, value = "Manager id")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/cancel/{receiptId}/manager/{managerId}")
    ReceiptModel cancelOrRenewReceipt(@PathVariable Long receiptId, @PathVariable Long managerId);

}
