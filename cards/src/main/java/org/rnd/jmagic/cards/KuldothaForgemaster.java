package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kuldotha Forgemaster")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("5")
@ColorIdentity({})
public final class KuldothaForgemaster extends Card
{
	public static final class KuldothaForgemasterAbility0 extends ActivatedAbility
	{
		public KuldothaForgemasterAbility0(GameState state)
		{
			super(state, "(T), Sacrifice three artifacts: Search your library for an artifact card and put it onto the battlefield. Then shuffle your library.");
			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 3, ArtifactPermanents.instance(), "Sacrifice three artifacts"));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card and put it onto the battlefield.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));
			this.addEffect(search);
		}
	}

	public KuldothaForgemaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// (T), Sacrifice three artifacts: Search your library for an artifact
		// card and put it onto the battlefield. Then shuffle your library.
		this.addAbility(new KuldothaForgemasterAbility0(state));
	}
}
