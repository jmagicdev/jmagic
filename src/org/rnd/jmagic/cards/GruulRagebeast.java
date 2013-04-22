package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gruul Ragebeast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulRagebeast extends Card
{
	public static final class GruulRagebeastAbility0 extends EventTriggeredAbility
	{
		public GruulRagebeastAbility0(GameState state)
		{
			super(state, "Whenever Gruul Ragebeast or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), ControllerOf.instance(ABILITY_SOURCE_OF_THIS), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance()), "target creature an opponent controls"));
			this.addEffect(fight(Union.instance(thatCreature, target), "That creature fights target creature an opponent controls."));
		}
	}

	public GruulRagebeast(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Whenever Gruul Ragebeast or another creature enters the battlefield
		// under your control, that creature fights target creature an opponent
		// controls.
		this.addAbility(new GruulRagebeastAbility0(state));
	}
}
