package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.MovieDOWithBLOBs;

import java.util.List;

@Data
public class MoviesListDTO {
    private Long count;
    private List<MovieDOWithBLOBs> movieDOList;
}
