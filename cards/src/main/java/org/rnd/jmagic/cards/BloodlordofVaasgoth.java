package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Bloodlord of Vaasgoth")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class BloodlordofVaasgoth extends Card
{
	public static final class Bloodthirst3 extends org.rnd.jmagic.abilities.keywords.Bloodthirst
	{
		public Bloodthirst3(GameState state)
		{
			super(state, 3);
		}
	}

	public static final class BloodlordofVaasgothAbility2 extends EventTriggeredAbility
	{
		public BloodlordofVaasgothAbility2(GameState state)
		{
			super(state, "Whenever you cast a Vampire creature spell, it gains bloodthirst 3.");

			SetGenerator vampireSpell = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), HasType.instance(Type.CREATURE));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, vampireSpell);
			this.addPattern(pattern);

			SetGenerator thatSpell = EventResult.instance(TriggerEvent.instance(This.instance()));
			this.addEffect(addAbilityUntilEndOfTurn(thatSpell, Bloodthirst3.class, "That spell gains bloodthirst 3."));
		}
	}

	public BloodlordofVaasgoth(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Bloodthirst 3 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with three +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 3));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a Vampire creature spell, it gains bloodthirst 3.
		this.addAbility(new BloodlordofVaasgothAbility2(state));
	}
}
