package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nature's Chosen")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Alliances.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class NaturesChosen extends Card
{
	public static final class GrantNaturalUntap extends StaticAbility
	{
		public static final class NaturalUntap extends ActivatedAbility
		{
			public NaturalUntap(GameState state)
			{
				super(state, "(0): Untap this creature. Activate this ability only during your turn and only once each turn.");

				// Untap this creature.
				this.addEffect(untap(AbilitySource.instance(This.instance()), "Untap this creature."));

				// Activate this ability only during your turn...
				SetGenerator itsYourTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance()));
				this.addActivateRestriction(Not.instance(itsYourTurn));

				// ... and only once each turn.
				this.perTurnLimit(1);
			}
		}

		public GrantNaturalUntap(GameState state)
		{
			super(state, "Enchanted creature has \"(0): Untap this creature. Activate this ability only during your turn and only once each turn.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), NaturalUntap.class));
		}
	}

	public static final class GrantTapExchange extends StaticAbility
	{
		public static final class TapExchange extends ActivatedAbility
		{
			public TapExchange(GameState state)
			{
				super(state, "(T): Untap target artifact, creature, or land. Activate this ability only once each turn.");

				// (T):
				this.costsTap = true;

				// Untap target artifact, creature, or land.
				Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), LandPermanents.instance()), "target artifact, creature, or land");
				this.addEffect(untap(targetedBy(target), "Untap target artifact, creature, or land."));

				// Activate this ability only once each turn.
				this.perTurnLimit(1);
			}
		}

		public GrantTapExchange(GameState state)
		{
			super(state, "As long as enchanted creature is white, it has \"(T): Untap target artifact, creature, or land. Activate this ability only once each turn.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.canApply = Both.instance(this.canApply, Intersect.instance(enchantedCreature, HasColor.instance(Color.WHITE)));

			this.addEffectPart(addAbilityToObject(enchantedCreature, TapExchange.class));
		}
	}

	public NaturesChosen(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// Enchanted creature has "(0): Untap this creature. Activate this
		// ability only during your turn and only once each turn."
		this.addAbility(new GrantNaturalUntap(state));

		// As long as enchanted creature is white, it has "(T): Untap target
		// artifact, creature, or land. Activate this ability only once each
		// turn."
		this.addAbility(new GrantTapExchange(state));
	}
}
