package com.goodcodeforfun.simplejsonviewer.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.goodcodeforfun.simplejsonviewer.Configuration;
import com.goodcodeforfun.simplejsonviewer.R;
import com.goodcodeforfun.simplejsonviewer.SimpleJSONViewerApplication;
import com.goodcodeforfun.simplejsonviewer.models.ExchangeRate;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.goodcodeforfun.simplejsonviewer.Configuration.FROM_JSON_OBJECT_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.RATES_JSON_FILE_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.RATE_JSON_OBJECT_NAME;
import static com.goodcodeforfun.simplejsonviewer.Configuration.TO_JSON_OBJECT_NAME;

/**
 * Created by snigavig on 15.07.16.
 */
public class CurrencyConverter implements LoaderManager.LoaderCallbacks<JSONArray> {

    private static final String LOG_TAG = "CurrencyConverter";
    private static final int RATES_LOADER_ID = 1;
    private final WeakReference<AppCompatActivity> activityWeakReference;
    private List<Vertex> nodes;
    private List<Edge> edges;
    private boolean isConverterReady= false;

    public CurrencyConverter(AppCompatActivity context) {
        this.activityWeakReference = new WeakReference<>(context);
        activityWeakReference.get().getSupportLoaderManager().initLoader(RATES_LOADER_ID, null, this);
    }

    public Double convertToGlobal(@NonNull String currencyCode, Double amount) {
        Double result = 0.0d;
        if (isConverterReady) {
            if (null != amount) {
                result = amount;
            }
            Graph graph = new Graph(nodes, edges);
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
            dijkstra.execute(getVertexByName(currencyCode));
            LinkedList<Vertex> path = dijkstra.getPath(getVertexByName(Configuration.GLOBAL_CURRENCY));

            if (null != path && path.size() > 0) {
                String from = null;
                String to = null;
                for (Vertex vertex : path) {
                    if (null == from) {
                        from = vertex.getName();
                    } else if (null == to) {
                        to = vertex.getName();
                        Edge edge = getEdgeById(from + to);
                        if (null != edge)
                            result *= edge.getWeight();
                    } else {
                        from = to;
                        to = vertex.getName();
                        Edge edge = getEdgeById(from + to);
                        if (null != edge)
                            result *= edge.getWeight();
                    }

                }
            }
            return result;
        } else {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            SimpleJSONViewerApplication.getInstance(),
                            activityWeakReference.get().getString(R.string.converter_is_not_ready_error),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
        return result;
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<JSONArray>(activityWeakReference.get()) {

            private JSONArray jsonArray;

            @Override
            protected void onStartLoading() {
                if (jsonArray == null) {
                    forceLoad();
                } else {
                    deliverResult(jsonArray);
                }
            }

            @Override
            public void deliverResult(JSONArray jsonArray) {
                if (jsonArray != null)
                    this.jsonArray = jsonArray;
                super.deliverResult(jsonArray);
            }

            @Override
            public JSONArray loadInBackground() {
                try {
                    return JSONUtils.getJSONFromFile(RATES_JSON_FILE_NAME);
                } catch (JSONUtils.EmptyJSONFileException e) {
                    activityWeakReference.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    SimpleJSONViewerApplication.getInstance(),
                                    activityWeakReference.get().getString(R.string.empty_file_error),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
        ArrayList<ExchangeRate> rates = new ArrayList<>();
        if (data != null) {
            try {
                for (int i = 0; i < data.length(); i++) {
                    String from = String.valueOf(data.getJSONObject(i).get(FROM_JSON_OBJECT_NAME));
                    Double rate = JSONUtils.parseDouble(String.valueOf(data.getJSONObject(i).get(RATE_JSON_OBJECT_NAME)));
                    String to = String.valueOf(data.getJSONObject(i).get(TO_JSON_OBJECT_NAME));
                    if (null != from && null != to){
                        ExchangeRate exchangeRate = new ExchangeRate();
                        exchangeRate.setFrom(from);
                        exchangeRate.setRate(rate);
                        exchangeRate.setTo(to);
                        rates.add(exchangeRate);
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            }
        }
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        for (ExchangeRate rate : rates) {
            boolean isVertexInitialized = false;
            for (Vertex vertex : nodes) {
                if (vertex.getId() != null && vertex.getId().contains(rate.getFrom())) {
                    isVertexInitialized = true;
                }
            }
            if (!isVertexInitialized) {
                Vertex location = new Vertex(rate.getFrom(), rate.getFrom());
                nodes.add(location);
            }
        }

        for (ExchangeRate rate : rates) {
            Edge edge = new Edge(rate.getFrom() + rate.getTo(), getVertexByName(rate.getFrom()), getVertexByName(rate.getTo()), rate.getRate());
            edges.add(edge);
        }

        isConverterReady = true;
    }

    private Edge getEdgeById(String search) {
        if (null != edges) {
            for (Edge edge : edges) {
                if (edge.getId() != null && edge.getId().contains(search))
                    return edge;
            }
        }
        return null;
    }

    private Vertex getVertexByName(String search) {
        if (null != nodes) {
            for (Vertex vertex : nodes) {
                if (vertex.getName() != null && vertex.getName().contains(search))
                    return vertex;
            }
        }
        return null;
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

    }
}
