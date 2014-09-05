package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Roil Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3UUU")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class RoilElemental extends Card
{
	public static final class LandfallSower extends EventTriggeredAbility
	{
		public LandfallSower(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may gain control of target creature for as long as you control Roil Elemental.");
			this.addPattern(landfall());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			SetGenerator expires = Intersect.instance(This.instance(), ControlledBy.instance(You.instance()));

			this.addEffect(youMay(createFloatingEffect(expires, "Gain control of target creature for as long as you control Roil Elemental", part), "You may gain control of target creature for as long as you control Roil Elemental."));
		}
	}

	public RoilElemental(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may gain control of target creature for as long as you
		// control Roil Elemental.
		this.addAbility(new LandfallSower(state));
	}
}
