package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Loxodon Gatekeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.SOLDIER})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LoxodonGatekeeper extends Card
{
	public static final class Gatekeeping extends StaticAbility
	{
		public Gatekeeping(GameState state)
		{
			super(state, "Artifacts, creatures, and lands your opponents control enter the battlefield tapped.");
			SetGenerator controller = ControllerOf.instance(This.instance());

			SetGenerator stuff = HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND);
			SetGenerator opponents = OpponentsOf.instance(controller);

			ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Artifacts, creatures, and lands your opponents control enter the battlefield tapped");
			gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), stuff, opponents, false));
			gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "An artifact, creature, or land an opponent controls enters the battlefield tapped."));
			this.addEffectPart(replacementEffectPart(gatekeeping));
		}
	}

	public LoxodonGatekeeper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new Gatekeeping(state));
	}
}
