package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crackleburr")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1(U/R)(U/R)")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Crackleburr extends Card
{
	public static final class CrackleburrAbility0 extends ActivatedAbility
	{
		public CrackleburrAbility0(GameState state)
		{
			super(state, "(UR)(UR), (T), Tap two untapped red creatures you control: Crackleburr deals 3 damage to target creature or player.");
			this.setManaCost(new ManaPool("(UR)(UR)"));
			this.costsTap = true;

			SetGenerator yourUntappedCreatures = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);
			SetGenerator yourUntappedRedCreatures = Intersect.instance(yourUntappedCreatures, HasColor.instance(Color.RED));

			EventFactory tapCreatures = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped red creatures you control");
			tapCreatures.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapCreatures.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapCreatures.parameters.put(EventType.Parameter.CHOICE, yourUntappedRedCreatures);
			tapCreatures.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(tapCreatures);

			Target t = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(t), "Crackleburr deals 3 damage to target creature or player."));
		}
	}

	public static final class CrackleburrAbility1 extends ActivatedAbility
	{
		public CrackleburrAbility1(GameState state)
		{
			super(state, "(UR)(UR), (Q), Untap two tapped blue creatures you control: Return target creature to its owner's hand.");
			this.setManaCost(new ManaPool("(UR)(UR)"));
			this.costsUntap = true;

			SetGenerator yourTappedCreatures = Intersect.instance(Tapped.instance(), CREATURES_YOU_CONTROL);
			SetGenerator yourTappedBlueCreatures = Intersect.instance(yourTappedCreatures, HasColor.instance(Color.BLUE));

			EventFactory untapCreatures = new EventFactory(EventType.UNTAP_CHOICE, "Untap two tapped blue creatures you control");
			untapCreatures.parameters.put(EventType.Parameter.CAUSE, This.instance());
			untapCreatures.parameters.put(EventType.Parameter.PLAYER, You.instance());
			untapCreatures.parameters.put(EventType.Parameter.CHOICE, yourTappedBlueCreatures);
			untapCreatures.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(untapCreatures);

			Target t = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(bounce(targetedBy(t), "Return target creature to its owner's hand."));
		}
	}

	public Crackleburr(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// ((u/r)((u/r)), (T), Tap two untapped red creatures you control:
		// Crackleburr deals 3 damage to target creature or player.
		this.addAbility(new CrackleburrAbility0(state));

		// ((u/r)((u/r)), (Q), Untap two tapped blue creatures you control:
		// Return target creature to its owner's hand. ((Q) is the untap
		// symbol.)
		this.addAbility(new CrackleburrAbility1(state));
	}
}
