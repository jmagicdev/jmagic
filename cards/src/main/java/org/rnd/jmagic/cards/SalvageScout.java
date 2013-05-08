package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Salvage Scout")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SalvageScout extends Card
{
	public static final class SalvageScoutAbility0 extends ActivatedAbility
	{
		public SalvageScoutAbility0(GameState state)
		{
			super(state, "(W), Sacrifice Salvage Scout: Return target artifact card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(W)"));
			this.addCost(sacrificeThis("Salvage Scout"));

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(yourGraveyard)), "target artifact card in your graveyard"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target artifact card from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public SalvageScout(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (W), Sacrifice Salvage Scout: Return target artifact card from your
		// graveyard to your hand.
		this.addAbility(new SalvageScoutAbility0(state));
	}
}
