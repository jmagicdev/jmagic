package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bear Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BearUmbra extends Card
{
	public static final class BearPump extends StaticAbility
	{
		public static final class UntapLands extends EventTriggeredAbility
		{
			public UntapLands(GameState state)
			{
				super(state, "Whenever this creature attacks, untap all lands you control.");

				this.addPattern(whenThisAttacks());

				this.addEffect(untap(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())), "Untap all lands you control."));
			}
		}

		public BearPump(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"Whenever this creature attacks, untap all lands you control.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +2, +2));

			this.addEffectPart(addAbilityToObject(enchantedCreature, UntapLands.class));
		}
	}

	public BearUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has "Whenever this creature
		// attacks, untap all lands you control."
		this.addAbility(new BearPump(state));

		// Totem armor
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
