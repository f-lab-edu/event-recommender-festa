package com.festa.controller;

import com.festa.dto.HostDTO;
import com.festa.service.HostService;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/hosts")
@Log4j2
public class HostController {

    @Autowired
    private HostService hostService;

    /**
     * 주최자 회원가입 기능
     * @param hostDTO
     * @return ResponseEntity
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<HostDTO> signUpAsHost(@RequestBody @NotNull HostDTO hostDTO) {
        hostService.insertHostInfo(hostDTO);

        URI uri = WebMvcLinkBuilder.linkTo(HostController.class).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 주최자 중복 아이디 체크
     * @param id
     * @return HttpStatus
     */
    @GetMapping("/{id}/duplicate")
    public HttpStatus idIsDuplicated(@PathVariable @NotNull String id) {
        int isDuplicated = hostService.idIsDuplicated(id);

        //1을 리턴 받았다면 true이므로 id가 존재한다.
        if(isDuplicated == 1) {
            return HttpStatus.CONFLICT;
        } else {
            return HttpStatus.OK;
        }
    }
}
