package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.model.health.Examination;

import java.util.List;

public class ExaminationRowStyleHelper {

	public static String getRowStyleClasses(List<Examination> list) {
		StringBuffer buffer = new StringBuffer();

		boolean first = true;
		for (Examination examination : list) {
			if (first) {
				first = false;
			} else {
				buffer.append(", ");
			}
			buffer.append(getRowStyleClass(examination));
		}

		return buffer.toString();
	}

	public static String getRowStyleClass(Examination examination) {
		if (examination == null)
			return "";

		switch (examination.getStatus()) {
		case TODO:
			switch (examination.getAppointment().getStatus()) {
			case BOOKED:
				return "booked";
			case ACCEPTED:
			default:
				return "success";
			}
		case RUNNING:
			return "error";
		case SUSPENDED:
			return "warning";
		case DONE:
			return "info";
		case CONCLUDED:
			return "concluded";
		default:
			return "base";
		}
	}

}
