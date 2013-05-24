package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Krosan Drover")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KrosanDrover extends Card
{
	public static final class KrosanDroverAbility0 extends StaticAbility
	{
		public KrosanDroverAbility0(GameState state)
		{
			super(state, "Creature spells you cast with converted mana cost 6 or more cost (2) less to cast.");

			SetGenerator spellsYouCast = Intersect.instance(Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance()));
			SetGenerator stuff = Intersect.instance(HasType.instance(Type.CREATURE), spellsYouCast, HasConvertedManaCost.instance(Between.instance(6, null)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, stuff);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			this.addEffectPart(part);
		}
	}

	public KrosanDrover(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Creature spells you cast with converted mana cost 6 or more cost (2)
		// less to cast.
		this.addAbility(new KrosanDroverAbility0(state));
	}
}
