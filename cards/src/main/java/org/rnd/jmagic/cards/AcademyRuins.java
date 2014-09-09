package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Academy Ruins")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@ColorIdentity({Color.BLUE})
public final class AcademyRuins extends Card
{
	public static final class GraveyardTutorArtifact extends ActivatedAbility
	{
		public GraveyardTutorArtifact(GameState state)
		{
			super(state, "(1)(U), (T): Put target artifact card in your graveyard on top of your library.");

			this.costsTap = true;
			this.setManaCost(new ManaPool("(1)(U)"));

			SetGenerator artifacts = HasType.instance(Type.ARTIFACT);
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			Target target = this.addTarget(Intersect.instance(artifacts, inYourGraveyard), "target artifact card in your graveyard");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.PUT_INTO_LIBRARY, parameters, "Put target artifact card in your graveyard on top of your library."));
		}
	}

	public AcademyRuins(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new GraveyardTutorArtifact(state));
	}

}
