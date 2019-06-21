package monitorServer.dal;



import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import monitorServer.storage.Report;

@RepositoryRestResource
public interface ReportDao extends PagingAndSortingRepository<Report, String> {

	
	public List<Report> findAllByDateGreaterThanAndType(Date fromDate, String type);

	public List<Report> findAllByDateGreaterThan(Date fromDate);

	public List<Report> findAllByType(String type);

}
