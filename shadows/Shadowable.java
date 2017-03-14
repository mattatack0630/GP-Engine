package shadows;

import models.StaticModel;
import rendering.RenderData;

/**
 * Created by mjmcc on 1/15/2017.
 */
public interface Shadowable
{
	RenderData getRenderData();

	StaticModel getShadowModel();
}
