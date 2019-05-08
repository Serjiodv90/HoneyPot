/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterfaceService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import userInterfaceService.domain.OrganizationUser;

public interface UserRepository extends MongoRepository<OrganizationUser, String> {
    
    OrganizationUser findByEmail(String email);
    OrganizationUser findByOrganization(String organization);
    
}
