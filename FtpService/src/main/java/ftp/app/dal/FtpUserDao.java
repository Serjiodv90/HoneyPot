package ftp.app.dal;

import org.springframework.data.repository.PagingAndSortingRepository;

import ftp.app.dblink.FtpUser;

public interface FtpUserDao extends PagingAndSortingRepository<FtpUser, String>{

}
