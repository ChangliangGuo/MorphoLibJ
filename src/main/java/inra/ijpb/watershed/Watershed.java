package inra.ijpb.watershed;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

/**
 * Several static methods for computing watershed in 2D/3D images. 
 * @author Ignacio Arganda-Carreras
 */
public class Watershed 
{
	/**
	 * Compute fast watershed using flooding simulations, as described by 
	 * Soille, Pierre, and Luc M. Vincent. "Determining watersheds in 
	 * digital pictures via flooding simulations." Lausanne-DL tentative. 
	 * International Society for Optics and Photonics, 1990.
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param mask binary mask to restrict the regions of interest
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @return image of labeled catchment basins with dams (labels are 1, 2, ...)
	 */
	public static ImagePlus computeWatershed( 
			ImagePlus input,
			ImagePlus mask,
			int connectivity )
	{
		WatershedTransform3D wt = new WatershedTransform3D( input, mask, connectivity );
		
		return wt.apply();		
	}
	
	/**
	 * Compute watershed with markers with an optional binary mask
	 * to restrict the regions of application
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param binaryMask binary mask to restrict the regions of interest
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImagePlus computeWatershed( 
			ImagePlus input,
			ImagePlus marker,
			ImagePlus binaryMask,
			int connectivity,
			boolean usePriorityQueue )
	{
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( input, marker, binaryMask, connectivity );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue();
		else
			return wt.applyWithSortedList();
	}
	
	/**
	 * Compute watershed with markers with an optional binary mask
	 * to restrict the regions of application
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param binaryMask binary mask to restrict the regions of interest
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImageStack computeWatershed( 
			ImageStack input,
			ImageStack marker,
			ImageStack binaryMask,
			int connectivity,
			boolean usePriorityQueue )
	{		
		final ImagePlus inputIP = new ImagePlus( "input", input );
		final ImagePlus markerIP = new ImagePlus( "marker", marker );
		final ImagePlus binaryMaskIP = ( null != binaryMask ) ? new ImagePlus( "binary mask", binaryMask ) : null;
		
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( inputIP, markerIP, binaryMaskIP, connectivity );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue().getImageStack();
		else
			return wt.applyWithSortedList().getImageStack();
	}
	
	/**
	 * Compute watershed with markers with an optional binary mask
	 * to restrict the regions of application
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param binaryMask binary mask to restrict the regions of interest
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImageProcessor computeWatershed( 
			ImageProcessor input,
			ImageProcessor marker,
			ImageProcessor binaryMask,
			int connectivity,
			boolean usePriorityQueue )
	{		
		final ImagePlus inputIP = new ImagePlus( "input", input );
		final ImagePlus markerIP = new ImagePlus( "marker", marker );
		final ImagePlus binaryMaskIP = ( null != binaryMask ) ? new ImagePlus( "binary mask", binaryMask ) : null;
		
		final int conn3d = connectivity == 4 ? 6 : 26;
									
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( inputIP, markerIP, binaryMaskIP, conn3d );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue().getProcessor();
		else
			return wt.applyWithSortedList().getProcessor();
	}

	/**
	 * Compute watershed with markers
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImagePlus computeWatershed( 
			ImagePlus input,
			ImagePlus marker,
			int connectivity,
			boolean usePriorityQueue )
	{
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( input, marker, null, connectivity );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue();
		else
			return wt.applyWithSortedList();
	}
	
	/**
	 * Compute watershed with markers
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImageStack computeWatershed( 
			ImageStack input,
			ImageStack marker,
			int connectivity,
			boolean usePriorityQueue )
	{		
		final ImagePlus inputIP = new ImagePlus( "input", input );
		final ImagePlus markerIP = new ImagePlus( "marker", marker );	
		
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( inputIP, markerIP, null, connectivity );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue().getImageStack();
		else
			return wt.applyWithSortedList().getImageStack();
	}
	
	/**
	 * Compute watershed with markers
	 * 
	 * @param input original grayscale image (usually a gradient image)
	 * @param marker image with labeled markers
	 * @param connectivity voxel connectivity to define neighborhoods
	 * @param usePriorityQueue select/deselect the use of the algorithm based on a priority queue
	 * @return image of labeled catchment basins (labels are 1, 2, ...)
	 */
	public static ImageProcessor computeWatershed( 
			ImageProcessor input,
			ImageProcessor marker,
			int connectivity,
			boolean usePriorityQueue )
	{		
		final ImagePlus inputIP = new ImagePlus( "input", input );
		final ImagePlus markerIP = new ImagePlus( "marker", marker );
		
		final int conn3d = connectivity == 4 ? 6 : 26;
									
		MarkerControlledWatershedTransform3D wt = new MarkerControlledWatershedTransform3D( inputIP, markerIP, null, conn3d );
		if( usePriorityQueue )
			return wt.applyWithPriorityQueue().getProcessor();
		else
			return wt.applyWithSortedList().getProcessor();
	}

}
