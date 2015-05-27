package com.chinatelecom.ctsi.workhelper.activity;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.view.Line;
import com.chinatelecom.ctsi.workhelper.view.LineGraph;
import com.chinatelecom.ctsi.workhelper.view.LinePoint;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

public class MeHuoyue extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_huoyue);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Line l = new Line();
		LinePoint p = new LinePoint();
		p.setX(0);
		p.setY(5);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(1);
		p.setY(8);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(2);
		p.setY(4);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(3);
		p.setY(6);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(7);
		p.setY(5);
		l.addPoint(p);
		l.setColor(Color.parseColor("#FFBB33"));
		l.setShowingPoints(true);
		l.isShowingPoints();
		LineGraph li = (LineGraph)findViewById(R.id.linegraph);
		li.addLine(l);
		li.setRangeY(0, 10);
		li.setLineToFill(0);

		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
