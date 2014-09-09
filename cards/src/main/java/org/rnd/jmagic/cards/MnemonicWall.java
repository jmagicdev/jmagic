package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mnemonic Wall")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class MnemonicWall extends Card
{
	public static final class MnemonicWallAbility1 extends EventTriggeredAbility
	{
		public MnemonicWallAbility1(GameState state)
		{
			super(state, "When Mnemonic Wall enters the battlefield, you may return target instant or sorcery card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator spellsInYard = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(yourGraveyard));
			SetGenerator target = targetedBy(this.addTarget(spellsInYard, "target instant or sorcery card from your graveyard"));

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target instant or sorcery card from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(youMay(move, "You may return target instant or sorcery card from your graveyard to your hand."));
		}
	}

	public MnemonicWall(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// When Mnemonic Wall enters the battlefield, you may return target
		// instant or sorcery card from your graveyard to your hand.
		this.addAbility(new MnemonicWallAbility1(state));
	}
}
