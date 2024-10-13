package com.example.Threading.Huddle;

import com.example.Threading.Helpers.SystemMapper;
import com.example.Threading.Huddle.Dto.HuddleCreateDto;
import com.example.Threading.Huddle.Dto.HuddleGetDto;
import com.example.Threading.Huddle.Dto.HuddleUpdateDto;
import com.example.Threading.HuddleMember.HuddleMemberService;
import com.example.Threading.Users.AppUserService;
import com.example.Threading.Users.Dto.AppUserGetDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/huddles")
public class HuddleController {
    private final HuddleService huddleService;
    private final HuddleMemberService huddleMemberService;
    private final AppUserService appUserService;
    public HuddleController(HuddleService huddleService, HuddleMemberService huddleMemberService, AppUserService appUserService) {
        this.huddleService = huddleService;
        this.huddleMemberService = huddleMemberService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<?> getHuddles() {
        return new ResponseEntity<>(huddleService.getHuddles(appUserService.getCurrentUser().getUuid()), HttpStatus.OK);
    }
    @GetMapping("/my-huddles")
    public ResponseEntity<?> getMyHuddles() {
        return new ResponseEntity<>(huddleService.getAllHuddlesOfLoggedInUser(appUserService.getCurrentUser().getUuid()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HuddleCreateDto dto) {
        huddleService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/add-user")
    public ResponseEntity<?> addUserToHuddle(@RequestBody HuddleUpdateDto updateDto){
        huddleService.addUsersToHuddle(updateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{huddleUuid}/remove-user")
    public ResponseEntity<?> removeUser(@PathVariable("huddleUuid") UUID huddleUuid, @RequestParam("userUuid") UUID userUuid){
        huddleMemberService.removeUser(userUuid, huddleUuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{uuid}/members")
    public ResponseEntity<?> getHuddleMembers(@PathVariable("uuid") UUID uuid){
        return new ResponseEntity<>(huddleMemberService.getHuddleUserList(uuid), HttpStatus.OK);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getHuddle(@PathVariable("uuid") UUID uuid){
        return new ResponseEntity<>(SystemMapper.toDto(huddleService.getById(uuid), HuddleGetDto.class), HttpStatus.OK);
    }
}
