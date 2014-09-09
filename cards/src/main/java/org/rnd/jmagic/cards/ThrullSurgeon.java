package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thrull Surgeon")
@Types({Type.CREATURE})
@SubTypes({SubType.THRULL})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class ThrullSurgeon extends Card
{
	public static final class ThrullSurgeonAbility extends ActivatedAbility
	{
		public ThrullSurgeonAbility(GameState state)
		{
			super(state, "(1)(B), Sacrifice Thrull Surgeon: Look at target player's hand and choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.");

			this.setManaCost(new ManaPool("1B"));

			this.addCost(sacrificeThis("Thrull Surgeon"));

			Target target = this.addTarget(Players.instance(), "target player");
			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, You.instance());
			discardParameters.put(EventType.Parameter.TARGET, targetedBy(target));
			this.addEffect(new EventFactory(EventType.DISCARD_FORCE, discardParameters, "Look at target player's hand and choose a card from it. That player discards that card."));

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public ThrullSurgeon(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new ThrullSurgeonAbility(state));
	}
}
