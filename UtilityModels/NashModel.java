package UtilityModels;

public class NashModel implements iUtilitiesModel {

	@Override
	public double[] getUtilities(int size) {
		// TODO Auto-generated method stub
		double[] borda = ExponentialModel.BORDA.getUtilities(size);
		double[] out = new double[size];
		for (int i = 0; i < out.length; i++) {
			out[i] = Math.log(borda[i]);
		}
		out[out.length - 1] = -Double.MAX_VALUE;
		return out;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Nash Borda";
	}

}
