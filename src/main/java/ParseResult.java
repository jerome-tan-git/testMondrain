import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.layout.RectangularCellSetFormatter;








public class ParseResult {

	 public static void main(String[] args)throws FileNotFoundException, ClassNotFoundException, SQLException {
		  // TODO Auto-generated method stub
		 /**
		  * WITH MEMBER Measures.NorthAmerica AS SUM 
		      (
		         {[Geography].[Country].&[Canada]
		            , [Geography].[Country].&[United States]}
		       ,[Measures].[Reseller Sales Amount]
		      )
		SELECT {[Measures].[NorthAmerica]} ON 0,
		[Product].[Category].members ON 1
		FROM [Adventure Works]
		  */
		 final String query = "WITH MEMBER Measures.x AS SUM( {[yeard].[2015]}, [Measures].[salenum] ) SELECT {Measures.x} on 0, {[yeard].[2015],[yeard].[2014]} on 1 from salsecube";
	        
	        // Load the driver
	        Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
	        
	        // Connect
	        final Connection connection =
	            DriverManager.getConnection(
	            "jdbc:mondrian:"                                                            // Driver ident
	            + "Jdbc=jdbc:mysql://db103.dev.la1.vcinv.net:3306/jeromedw?user=dt_cms_all&password=Dt5Cy8MS;"                                // Relational DB
	            + "Catalog=file:Schema2.xml;");                                   // Catalog
	        
	     // We are dealing with an olap connection. we must unwrap it.
	        final OlapConnection olapConnection = connection.unwrap(OlapConnection.class);

	        // Prepare a statement.
	        final CellSet cellSet = olapConnection
	            .createStatement()              // Prepare a statement
	                .executeOlapQuery(query);   // Execute some query

	        // We use the utility formatter.
	        RectangularCellSetFormatter formatter =
	            new RectangularCellSetFormatter(false);

	        // Print out.
	        PrintWriter writer = new PrintWriter(System.out);
	        formatter.format(cellSet, writer);
	        writer.flush();
	 }
}
