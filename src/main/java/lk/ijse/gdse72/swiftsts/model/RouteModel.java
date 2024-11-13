package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.RouteDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteModel {

    public ArrayList<RouteDto> getAllRoutes() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Route");
        ArrayList<RouteDto> routeDtos = new ArrayList<>();

        while (rst.next()) {
            RouteDto routeDto = new RouteDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            routeDtos.add(routeDto);
        }

        return routeDtos;
    }

    public String getNextRouteId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT routeId FROM Route ORDER BY routeId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }
        return "R001";
    }

    public boolean saveRoute(RouteDto routeDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Route VALUES (?,?,?,?,?)",
                routeDto.getRouteId(),
                routeDto.getRouteName(),
                routeDto.getStartPoint(),
                routeDto.getDestination(),
                routeDto.getDayFee()
        );
    }

    public boolean updateRoute(RouteDto routeDto) throws SQLException {
        return CrudUtil.execute("UPDATE Route SET routeName=?, startPoint=?, destination=?, dayFee=? WHERE routeId=?",
                routeDto.getRouteName(),
                routeDto.getStartPoint(),
                routeDto.getDestination(),
                routeDto.getDayFee(),
                routeDto.getRouteId()
        );
    }

    public boolean deleteRoute(String routeId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Route WHERE routeId=?", routeId);
    }
}