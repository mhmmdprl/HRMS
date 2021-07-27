package com.kodlamaio.hrms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.RoleRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.RoleService;

@RestController
@RequestMapping("api/admin/role")
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("getRoles")
	public DataResult<List<RoleRequest>> getMyRole(HttpServletRequest httpServletRequest) {
		return this.roleService.getRoles();

	}

	@GetMapping("addOperation")
	public Result addOperation(@RequestParam Long roleId, @RequestParam Long operationId,
			HttpServletRequest httpServletRequest) {

		return this.roleService.addOperation(roleId, operationId);
	}

	@GetMapping("deleteOperation")
	public Result deleteOperation(@RequestParam Long roleId, @RequestParam Long operationId,
			HttpServletRequest httpServletRequest) {

		return this.roleService.deleteOperation(roleId, operationId);
	}
}
