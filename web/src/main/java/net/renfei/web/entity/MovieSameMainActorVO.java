package net.renfei.web.entity;

import lombok.Data;
import net.renfei.dao.entity.MovieDOWithBLOBs;

import java.util.ArrayList;
import java.util.List;


@Data
public class MovieSameMainActorVO {
    private List<List<MovieDOWithBLOBs>> movieDOWithBLOBs;

    public MovieSameMainActorVO(List<MovieDOWithBLOBs> movieDOWithBLOBs) {
        this.movieDOWithBLOBs = new ArrayList<>();
        if (movieDOWithBLOBs != null && movieDOWithBLOBs.size() > 0) {
            if (movieDOWithBLOBs.size() < 4) {
                this.movieDOWithBLOBs.add(movieDOWithBLOBs);
            } else {
                List<MovieDOWithBLOBs> temp = new ArrayList<>();
                for (int i = 0, j = 0; i < movieDOWithBLOBs.size(); i++) {
                    if (j < 4) {
                        temp.add(movieDOWithBLOBs.get(i));
                        j++;
                    } else {
                        //这里会丢失最后不满足4个部分，但相关电影并不重要
                        this.movieDOWithBLOBs.add(temp);
                        j = 0;
                    }
                }
            }
        }
    }
}
