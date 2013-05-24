package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trygon Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("1GU")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class TrygonPredator extends Card
{
	public static final class TrygonPredatorAbility1 extends EventTriggeredAbility
	{
		public TrygonPredatorAbility1(GameState state)
		{
			super(state, "Whenever Trygon Predator deals combat damage to a player, you may destroy target artifact or enchantment that player controls.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator legalTargets = Intersect.instance(HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT), ControlledBy.instance(thatPlayer));
			SetGenerator target = targetedBy(this.addTarget(legalTargets, "target artifact or enchantment that player controls"));

			this.addEffect(youMay(destroy(target, "Destroy target artifact or enchantment that player controls"), "You may destroy target artifact or enchantment that player controls."));
		}
	}

	public TrygonPredator(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Trygon Predator deals combat damage to a player, you may
		// destroy target artifact or enchantment that player controls.
		this.addAbility(new TrygonPredatorAbility1(state));
	}
}
