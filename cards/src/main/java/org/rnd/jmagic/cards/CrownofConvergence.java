package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crown of Convergence")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CrownofConvergence extends Card
{
	public static final class Pump extends StaticAbility
	{
		public Pump(GameState state)
		{
			super(state, "As long as the top card of your library is a creature card, creatures you control that share a color with that card get +1/+1");

			SetGenerator controller = ControllerOf.instance(This.instance());
			SetGenerator library = LibraryOf.instance(controller);
			SetGenerator topCardOfLibrary = TopCards.instance(numberGenerator(1), library);
			SetGenerator colorsOfTopCard = ColorsOf.instance(topCardOfLibrary);
			SetGenerator sharesAColor = HasColor.instance(colorsOfTopCard);

			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator creaturePermanents = CreaturePermanents.instance();
			SetGenerator creaturesYouControl = Intersect.instance(ControlledBy.instance(controller), creaturePermanents);

			SetGenerator creaturesYouControlThatShareAColor = Intersect.instance(creaturesYouControl, sharesAColor);

			this.addEffectPart(modifyPowerAndToughness(creaturesYouControlThatShareAColor, 1, 1));

			this.canApply = Both.instance(this.canApply, Intersect.instance(creatures, topCardOfLibrary));
		}
	}

	public static final class Dig extends ActivatedAbility
	{
		public Dig(GameState state)
		{
			super(state, "(G)(W): Put the top card of your library on the bottom of your library");

			this.setManaCost(new ManaPool("GW"));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);
			SetGenerator library = LibraryOf.instance(controller);
			SetGenerator topCardOfLibrary = TopCards.instance(1, library);

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.TO, library);
			moveParameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			moveParameters.put(EventType.Parameter.OBJECT, topCardOfLibrary);
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Put the top card of your library on the bottom of your library"));
		}
	}

	public CrownofConvergence(GameState state)
	{
		super(state);

		this.addAbility(new RevealTopOfLibrary(state));
		this.addAbility(new Pump(state));
		this.addAbility(new Dig(state));
	}
}
