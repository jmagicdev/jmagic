package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demigod of Revenge")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.AVATAR})
@ManaCost("(B/R)(B/R)(B/R)(B/R)(B/R)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DemigodofRevenge extends Card
{
	public static final class DemigodofRevengeAbility1 extends EventTriggeredAbility
	{
		public DemigodofRevengeAbility1(GameState state)
		{
			super(state, "When you cast Demigod of Revenge, return all cards named Demigod of Revenge from your graveyard to the battlefield.");

			this.addPattern(whenYouCastThisSpell());

			EventFactory effect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return all cards named Demigod of Revenge from your graveyard to the battlefield.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, HasName.instance("Demigod of Revenge"));
			effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(effect);
		}
	}

	public DemigodofRevenge(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When you cast Demigod of Revenge, return all cards named Demigod of
		// Revenge from your graveyard to the battlefield.
		this.addAbility(new DemigodofRevengeAbility1(state));
	}
}
