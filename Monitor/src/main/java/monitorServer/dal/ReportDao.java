package monitorServer.dal;



import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import monitorServer.storage.Report;

@RepositoryRestResource
public interface ReportDao extends PagingAndSortingRepository<Report, String> {


}
