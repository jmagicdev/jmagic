package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Satyr Firedancer")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SATYR})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = BornOfTheGods.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SatyrFiredancer extends Card
{
	public static final class SatyrFiredancerAbility0 extends EventTriggeredAbility
	{
		public SatyrFiredancerAbility0(GameState state)
		{
			super(state, "Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.");

			SetGenerator yourSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), HasType.instance(Type.INSTANT, Type.SORCERY));
			SimpleDamagePattern spellDealsDamage = new SimpleDamagePattern(yourSpells, OpponentsOf.instance(You.instance()));
			this.addPattern(spellDealsDamage);

			SetGenerator thatMuch = Count.instance(TriggerDamage.instance(This.instance()));
			SetGenerator thatOpponent = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator legal = Intersect.instance(ControlledBy.instance(thatOpponent), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(legal, "target creature that player controls"));
			this.addEffect(permanentDealDamage(thatMuch, target, "Satyr Firedancer deals that much damage to target creature that player controls."));
		}
	}

	public SatyrFiredancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever an instant or sorcery spell you control deals damage to an
		// opponent, Satyr Firedancer deals that much damage to target creature
		// that player controls.
		this.addAbility(new SatyrFiredancerAbility0(state));
	}
}
