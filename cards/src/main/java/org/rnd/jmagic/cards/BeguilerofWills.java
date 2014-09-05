package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Beguiler of Wills")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class BeguilerofWills extends Card
{
	public static final class BeguilerofWillsAbility0 extends ActivatedAbility
	{
		public BeguilerofWillsAbility0(GameState state)
		{
			super(state, "(T): Gain control of target creature with power less than or equal to the number of creatures you control.");
			this.costsTap = true;

			SetGenerator allCreatures = HasPower.instance(Between.instance(Empty.instance(), Count.instance(CREATURES_YOU_CONTROL)));
			SetGenerator legal = Intersect.instance(allCreatures, CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(legal, "target creature with power less than or equal to the number of creatures you control"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			EventFactory effect = createFloatingEffect("Gain control of target creature with power less than or equal to the number of creatures you control.", part);
			this.addEffect(effect);
		}
	}

	public BeguilerofWills(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Gain control of target creature with power less than or equal to
		// the number of creatures you control.
		this.addAbility(new BeguilerofWillsAbility0(state));
	}
}
