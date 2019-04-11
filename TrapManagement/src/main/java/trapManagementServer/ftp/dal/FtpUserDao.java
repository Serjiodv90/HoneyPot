package trapManagementServer.ftp.dal;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import trapManagementServer.ftp.dbLink.FtpUser;

@RepositoryRestResource
public interface FtpUserDao extends PagingAndSortingRepository<FtpUser, String>{

}
