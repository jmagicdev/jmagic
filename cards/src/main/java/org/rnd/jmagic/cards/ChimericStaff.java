package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chimeric Staff")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({})
public final class ChimericStaff extends Card
{
	public static final class AnimateStaff extends ActivatedAbility
	{
		public AnimateStaff(GameState state)
		{
			super(state, "X: Chimeric Staff becomes an X/X Construct artifact creature until end of turn.");

			this.setManaCost(new ManaPool("X"));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator X = ValueOfX.instance(This.instance());

			ContinuousEffect.Part ptPart = setPowerAndToughness(thisCard, X, X);

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thisCard);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT, Type.CREATURE, SubType.CONSTRUCT));

			this.addEffect(createFloatingEffect("Chimeric Staff becomes an X/X Construct artifact creature until end of turn", ptPart, typesPart));
		}
	}

	public ChimericStaff(GameState state)
	{
		super(state);

		this.addAbility(new AnimateStaff(state));
	}
}
