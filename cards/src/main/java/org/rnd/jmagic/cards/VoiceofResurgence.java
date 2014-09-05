package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Voice of Resurgence")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS_MAZE, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class VoiceofResurgence extends Card
{
	public static final class VoiceTokenCDA extends CharacteristicDefiningAbility
	{
		public VoiceTokenCDA(GameState state)
		{
			super(state, "This creature's power and toughness are each equal to the number of creatures you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))));

			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public static final class VoiceofResurgenceAbility0 extends EventTriggeredAbility
	{
		private static final class CastDuringYourTurnPattern implements EventPattern
		{
			@Override
			public boolean match(Event event, Identified object, GameState state)
			{
				if(!EventType.BECOMES_PLAYED.equals(event.type))
					return false;
				GameObject cast = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, event.getSource()).getOne(GameObject.class);
				if(!(cast.isSpell()))
					return false;
				NonStaticAbility trigger = (NonStaticAbility)object;
				GameObject voice = (GameObject)(trigger.getSource(state));
				if(state.currentTurn().getOwner(state).equals(voice.getController(state)))
					return true;
				return false;
			}

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean matchesManaAbilities()
			{
				return false;
			}
		}

		public VoiceofResurgenceAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, put a green and white Elemental creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of creatures you control.\"");
			this.addPattern(new CastDuringYourTurnPattern());
			this.addPattern(whenThisDies());

			CreateTokensFactory elemental = new CreateTokensFactory(1, 0, 0, "Put a green and white Elemental creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of creatures you control.\"");
			elemental.setColors(Color.GREEN, Color.WHITE);
			elemental.setSubTypes(SubType.ELEMENTAL);
			elemental.addAbility(VoiceTokenCDA.class);
			this.addEffect(elemental.getEventFactory());
		}
	}

	public VoiceofResurgence(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, put a green and white Elemental creature token onto the battlefield with "This creature's power and toughness are each equal to the number of creatures you control."
		this.addAbility(new VoiceofResurgenceAbility0(state));
	}
}
