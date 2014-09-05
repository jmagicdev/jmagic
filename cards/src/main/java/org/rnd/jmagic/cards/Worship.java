package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Worship")
@Types({Type.ENCHANTMENT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Worship extends Card
{
	public static final class WorshipAbility0 extends StaticAbility
	{
		public WorshipAbility0(GameState state)
		{
			super(state, "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead.");

			this.canApply = Both.instance(this.canApply, CREATURES_YOU_CONTROL);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_REDUCE_LIFE_TOTAL_TO_LESS_THAN);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public Worship(GameState state)
	{
		super(state);

		// If you control a creature, damage that would reduce your life total
		// to less than 1 reduces it to 1 instead.
		this.addAbility(new WorshipAbility0(state));
	}
}
