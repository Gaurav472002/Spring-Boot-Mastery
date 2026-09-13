package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.api.request.AdminCreateRequest;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(
        name = "Admin APIs",
        description = "Administrative operations"
)
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AppCache appCache;


    @Operation(
            summary = "Get all users",
            description = "Returns all registered users"
    )
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Create admin",
            description = "Creates a new administrator. Requires ADMIN role. USER and ADMIN roles are assigned automatically."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Admin created successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Admin privileges required"
            )
    })
    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(
            @RequestBody AdminCreateRequest request) {

        userService.saveAdmin(request);

        return new ResponseEntity<>(
                "Admin created successfully",
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Clear application cache",
            description = "Clears and reloads the application cache"
    )
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
